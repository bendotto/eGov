/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.pgr.service.reports;

import static org.egov.pgr.utils.constants.PGRConstants.BYBOUNDARY;
import static org.egov.pgr.utils.constants.PGRConstants.FROMDATE;
import static org.egov.pgr.utils.constants.PGRConstants.TODATE;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.infra.config.persistence.datasource.routing.annotation.ReadOnly;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AgeingReportService {

    private static final String COMPLAINTSTATUS_COMPLETED = "Completed";
    private static final String COMPLAINTSTATUS_REJECTED = "Rejected";
    @PersistenceContext
    private EntityManager entityManager;

    @ReadOnly
    public SQLQuery getageingReportQuery(final DateTime fromDate, final DateTime toDate, final String typeofReport,
            final String complaintDateType, final String groupBy) {

        final StringBuilder query = new StringBuilder();

        if (groupBy != null && !"".equals(groupBy) && groupBy.equalsIgnoreCase(BYBOUNDARY))
            query.append("SELECT bndry.name as name, ");
        else
            query.append("SELECT dept.name as name, ");

        if (COMPLAINTSTATUS_COMPLETED.equals(typeofReport) || COMPLAINTSTATUS_REJECTED.equals(typeofReport)) {
            query.append(
                    " COUNT(CASE WHEN EXTRACT(EPOCH FROM state.lastmodifieddate - cd.createddate)/86400 > :grtthn30 THEN 1 END) grtthn30, ");
            query.append(
                    " COUNT(CASE WHEN EXTRACT(EPOCH FROM state.lastmodifieddate - cd.createddate)/86400 BETWEEN :grtthn10 AND :lsthn30 THEN 1 END) btw10to30, ");
            query.append(
                    " COUNT(CASE WHEN EXTRACT(EPOCH FROM state.lastmodifieddate - cd.createddate)/86400 BETWEEN :grtthn5 AND :lsthn10 THEN 1 END) btw5to10, ");
            query.append(
                    " COUNT(CASE WHEN EXTRACT(EPOCH FROM state.lastmodifieddate - cd.createddate)/86400 BETWEEN :grtthn2 AND :lsthn5 THEN 1 END) btw2to5, ");
            query.append(
                    " COUNT(CASE WHEN EXTRACT(EPOCH FROM state.lastmodifieddate - cd.createddate)/86400 BETWEEN :zero AND :lsthn2 THEN 1 END) lsthn2 ");
            query.append(" FROM egpgr_complaintstatus cs  ,egpgr_complainttype ctype, eg_wf_states state, egpgr_complaint cd  ");
        } else {
            query.append(" COUNT(CASE WHEN cd.createddate < :grtthn30 THEN 1 END) grtthn30, ");
            query.append(" COUNT(CASE WHEN cd.createddate BETWEEN :lsthn30 AND :grtthn10 THEN 1 END) btw10to30, ");
            query.append(" COUNT(CASE WHEN cd.createddate BETWEEN :lsthn10 AND  :grtthn5 THEN 1 END) btw5to10, ");
            query.append(" COUNT(CASE WHEN cd.createddate BETWEEN :lsthn5  AND  :grtthn2 THEN 1 END) btw2to5, ");
            query.append(" COUNT(CASE WHEN cd.createddate BETWEEN :lsthn2 AND :currdate THEN 1 END) lsthn2 ");
            query.append(" FROM egpgr_complaintstatus cs  ,egpgr_complainttype ctype ,egpgr_complaint cd  ");
        }

        if (BYBOUNDARY.equals(groupBy))
            query.append("  left JOIN eg_boundary bndry on cd.location =bndry.id ");
        else
            query.append("  left JOIN eg_department dept on cd.department =dept.id ");

        buildWhereClause(fromDate, toDate, typeofReport, complaintDateType, query);

        if (BYBOUNDARY.equals(groupBy))
            query.append("  group by bndry.name ");
        else
            query.append("  group by dept.name ");

        return setParameterForAgeingReport(query.toString(), typeofReport, fromDate, toDate, complaintDateType);
    }

    private void buildWhereClause(final DateTime fromDate, final DateTime toDate, final String typeofReport,
            final String complaintDateType, final StringBuilder query) {

        if (COMPLAINTSTATUS_COMPLETED.equals(typeofReport)) {
            query.append(" WHERE  cd.state_id=state.id and  cd.status  = cs.id and cd.complainttype= ctype.id  ");
            query.append(" AND cs.name IN ('COMPLETED', 'WITHDRAWN','CLOSED') ");
        } else if (COMPLAINTSTATUS_REJECTED.equals(typeofReport)) {
            query.append(" WHERE  cd.state_id=state.id and  cd.status  = cs.id and cd.complainttype= ctype.id  ");
            query.append(" AND cs.name IN ('REJECTED') ");
        } else {
            query.append(" WHERE cd.status  = cs.id and cd.complainttype= ctype.id  ");
            query.append(" AND cs.name IN ('REGISTERED','FORWARDED', 'PROCESSING','REOPENED') ");
        }

        if (fromDate != null && toDate != null)
            query.append(" and ( cd.createddate BETWEEN :fromDates and :toDates) ");
        else if ("lastsevendays".equals(complaintDateType) || "lastthirtydays".equals(complaintDateType)
                || "lastninetydays".equals(complaintDateType) || fromDate != null)
            query.append(" and cd.createddate >=   :fromDates ");
        else if (toDate != null)
            query.append(" and cd.createddate <=  :toDates ");
    }

    private SQLQuery setParameterForAgeingReport(final String querykey, final String typeofReport, final DateTime fromDate,
            final DateTime toDate, final String complaintDateType) {
        final SQLQuery qry = entityManager.unwrap(Session.class).createSQLQuery(querykey);

        if (COMPLAINTSTATUS_COMPLETED.equals(typeofReport) || COMPLAINTSTATUS_REJECTED.equals(typeofReport)) {
            qry.setParameter("grtthn30", 30);
            qry.setParameter("lsthn30", 30);
            qry.setParameter("grtthn10", 10);
            qry.setParameter("grtthn5", 5);
            qry.setParameter("lsthn10", 10);
            qry.setParameter("lsthn5", 5);
            qry.setParameter("lsthn2", 2);
            qry.setParameter("grtthn2", 2);
            qry.setParameter("zero", 0);

        } else {

            qry.setParameter("grtthn30", getCurrentDateWithEndOfDayTime().minusDays(30).toDate());
            qry.setParameter("grtthn10", getCurrentDateWithEndOfDayTime().minusDays(10).toDate());
            qry.setParameter("grtthn5", getCurrentDateWithEndOfDayTime().minusDays(5).toDate());
            qry.setParameter("grtthn2", getCurrentDateWithEndOfDayTime().minusDays(2).toDate());
            qry.setParameter("lsthn30", getCurrentDateWithOutTime().minusDays(29).toDate());
            qry.setParameter("lsthn10", getCurrentDateWithOutTime().minusDays(9).toDate());
            qry.setParameter("lsthn5", getCurrentDateWithOutTime().minusDays(4).toDate());
            qry.setParameter("lsthn2", getCurrentDateWithOutTime().minusDays(1).toDate());
            qry.setParameter("currdate", getCurrentDateWithEndOfDayTime().toDate());
        }

        if ("lastsevendays".equals(complaintDateType))
            qry.setParameter(FROMDATE, getCurrentDateWithOutTime().minusDays(7).toDate());
        else if ("lastthirtydays".equals(complaintDateType))
            qry.setParameter(FROMDATE, getCurrentDateWithOutTime().minusDays(30).toDate());
        else if ("lastninetydays".equals(complaintDateType))
            qry.setParameter(FROMDATE, getCurrentDateWithOutTime().minusDays(90).toDate());
        else if (fromDate != null && toDate != null) {
            qry.setParameter(FROMDATE, resetTimeByPassingDate(fromDate));
            qry.setParameter(TODATE, getEndOfDayByDate(toDate));
        } else if (fromDate != null)
            qry.setParameter(FROMDATE, resetTimeByPassingDate(fromDate));
        else if (toDate != null)
            qry.setParameter(TODATE, getEndOfDayByDate(toDate));
        return qry;
    }

    private Date getEndOfDayByDate(final DateTime fromDate) {
        return fromDate.withTime(23, 59, 59, 999).toDate();
    }

    private Date resetTimeByPassingDate(final DateTime fromDate) {
        return fromDate.withTime(0, 0, 0, 0).toDate();
    }

    private DateTime getCurrentDateWithOutTime() {
        return new LocalDateTime().withTime(0, 0, 0, 0).toDateTime();
    }

    private DateTime getCurrentDateWithEndOfDayTime() {
        return new LocalDateTime().withTime(23, 59, 59, 999).toDateTime();
    }

}
