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
package org.egov.works.web.controller.reports;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.egov.commons.CFinancialYear;
import org.egov.commons.service.CFinancialYearService;
import org.egov.infra.admin.master.service.BoundaryService;
import org.egov.infra.reporting.engine.ReportConstants.FileFormat;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.reporting.engine.ReportRequest;
import org.egov.infra.reporting.engine.ReportService;
import org.egov.works.master.service.ContractorService;
import org.egov.works.master.service.NatureOfWorkService;
import org.egov.works.models.masters.Contractor;
import org.egov.works.reports.entity.ContractorWiseAbstractReport;
import org.egov.works.reports.entity.ContractorWiseAbstractSearchResult;
import org.egov.works.reports.service.WorkProgressRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/reports")
public class ContractorWiseAbstractReportPDFController {

    public static final String CONTRACTOWISEABSTRACTREPORT = "contractorWiseAbstractReport";

    @Autowired
    private ReportService reportService;

    @Autowired
    private WorkProgressRegisterService workProgressRegisterService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CFinancialYearService cFinancialYearService;

    @Autowired
    private NatureOfWorkService natureOfWorkService;

    @Autowired
    private BoundaryService boundaryService;

    @Autowired
    private ContractorService contractorService;

    private final Map<String, Object> reportParams = new HashMap<String, Object>();
    private ReportRequest reportInput = null;
    private ReportOutput reportOutput = null;

    @RequestMapping(value = "/contractorwiseabstract/pdf", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<byte[]> generatePDFDepartmentWise(final HttpServletRequest request,
            @RequestParam("financialYearId") final Long financialYearId,
            @RequestParam("natureOfWork") final Long natureOfWork, @RequestParam("workStatus") final String workStatus,
            @RequestParam("ward") final Long ward, @RequestParam("contractor") final String contractor,
            @RequestParam("contentType") final String contentType, final HttpSession session) throws IOException {
        final ContractorWiseAbstractReport contractorWiseAbstractReport = new ContractorWiseAbstractReport();
        contractorWiseAbstractReport.setFinancialYearId(financialYearId);
        contractorWiseAbstractReport.setNatureOfWork(natureOfWork);
        contractorWiseAbstractReport.setWorkStatus(workStatus);
        contractorWiseAbstractReport.setContractor(contractor);
        contractorWiseAbstractReport.setElectionWardId(ward);
        final List<ContractorWiseAbstractSearchResult> contractorWiseAbstractList = workProgressRegisterService
                .searchContractorWiseAbstractReport(contractorWiseAbstractReport);

        return generateReport(contractorWiseAbstractList, request, session, contentType, contractorWiseAbstractReport);
    }

    private ResponseEntity<byte[]> generateReport(
            final List<ContractorWiseAbstractSearchResult> contractorWiseAbstractList, final HttpServletRequest request,
            final HttpSession session, final String contentType,
            final ContractorWiseAbstractReport contractorWiseAbstractReport) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        final SimpleDateFormat fomatter = new SimpleDateFormat("dd/MM/yyyy");
        reportParams.put("reportRunDate", sdf.format(new Date()));
        final List<ContractorWiseAbstractSearchResult> contractorSearchList = new ArrayList<ContractorWiseAbstractSearchResult>();

        for (final ContractorWiseAbstractSearchResult searchResult : contractorWiseAbstractList) {
            final ContractorWiseAbstractSearchResult contractorResult = new ContractorWiseAbstractSearchResult();
            if (searchResult != null) {
                if (searchResult.getElectionWard() != null)
                    contractorResult.setElectionWard(searchResult.getElectionWard());
                else
                    contractorResult.setElectionWard("NA");

                if (searchResult.getContractorName() != null)
                    contractorResult.setContractorName(searchResult.getContractorName());
                else
                    contractorResult.setContractorName("NA");

                if (searchResult.getContractorCode() != null)
                    contractorResult.setContractorCode(searchResult.getContractorCode());
                else
                    contractorResult.setContractorCode("NA");

                if (searchResult.getContractorClass() != null)
                    contractorResult.setContractorClass(searchResult.getContractorClass());
                else
                    contractorResult.setContractorClass("NA");

                if (searchResult.getApprovedEstimates() != null)
                    contractorResult.setApprovedEstimates(searchResult.getApprovedEstimates());
                else
                    contractorResult.setApprovedEstimates(0);

                if (searchResult.getApprovedAmount() != null)
                    contractorResult.setApprovedAmount(
                            searchResult.getApprovedAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
                else
                    contractorResult.setApprovedAmount(new BigDecimal(0));

                if (searchResult.getSiteNotHandedOverEstimates() != null)
                    contractorResult.setSiteNotHandedOverEstimates(searchResult.getSiteNotHandedOverEstimates());
                else
                    contractorResult.setSiteNotHandedOverEstimates(0);

                if (searchResult.getSiteNotHandedOverAmount() != null)
                    contractorResult.setSiteNotHandedOverAmount(
                            searchResult.getSiteNotHandedOverAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
                else
                    contractorResult.setSiteNotHandedOverAmount(new BigDecimal(0));

                if (searchResult.getNotWorkCommencedEstimates() != null)
                    contractorResult.setNotWorkCommencedEstimates(searchResult.getNotWorkCommencedEstimates());
                else
                    contractorResult.setNotWorkCommencedEstimates(0);

                if (searchResult.getNotWorkCommencedAmount() != null)
                    contractorResult.setNotWorkCommencedAmount(
                            searchResult.getNotWorkCommencedAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
                else
                    contractorResult.setNotWorkCommencedAmount(new BigDecimal(0));

                if (searchResult.getWorkCommencedEstimates() != null)
                    contractorResult.setWorkCommencedEstimates(searchResult.getWorkCommencedEstimates());
                else
                    contractorResult.setWorkCommencedEstimates(0);

                if (searchResult.getWorkCommencedAmount() != null)
                    contractorResult.setWorkCommencedAmount(
                            searchResult.getWorkCommencedAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
                else
                    contractorResult.setWorkCommencedAmount(new BigDecimal(0));

                if (searchResult.getWorkCompletedEstimates() != null)
                    contractorResult.setWorkCompletedEstimates(searchResult.getWorkCompletedEstimates());
                else
                    contractorResult.setWorkCompletedEstimates(0);

                if (searchResult.getWorkCompletedAmount() != null)
                    contractorResult.setWorkCompletedAmount(
                            searchResult.getWorkCompletedAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
                else
                    contractorResult.setWorkCompletedAmount(new BigDecimal(0));

                if (searchResult.getApprovedEstimates() != null && searchResult.getWorkCompletedEstimates() != null)
                    contractorResult.setBalanceWorkEstimates(searchResult.getApprovedEstimates().intValue()
                            - searchResult.getBalanceWorkEstimates().intValue());
                else if (searchResult.getApprovedEstimates() != null)
                    contractorResult.setBalanceWorkEstimates(searchResult.getApprovedEstimates());
                else
                    contractorResult.setBalanceWorkEstimates(0);

                if (searchResult.getApprovedAmount() != null && searchResult.getWorkCompletedAmount() != null)
                    contractorResult.setBalanceWorkAmount(searchResult.getApprovedAmount()
                            .subtract(searchResult.getWorkCompletedAmount()).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                else if (searchResult.getApprovedAmount() != null)
                    contractorResult.setBalanceWorkAmount(
                            searchResult.getApprovedAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
                else
                    contractorResult.setBalanceWorkAmount(new BigDecimal(0));

                if (searchResult.getLiableAmount() != null)
                    contractorResult
                            .setLiableAmount(searchResult.getLiableAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN));
                else
                    contractorResult.setLiableAmount(new BigDecimal(0));

                contractorSearchList.add(contractorResult);

            }
        }
        reportInput = new ReportRequest(CONTRACTOWISEABSTRACTREPORT, contractorSearchList, reportParams);
        String queryParameters = messageSource.getMessage("msg.contractorwiseabstractestimate.report", null, null);

        if (contractorWiseAbstractReport.getFinancialYearId() != null) {
            final CFinancialYear finyear = cFinancialYearService
                    .findOne(contractorWiseAbstractReport.getFinancialYearId());
            queryParameters += " " + messageSource.getMessage("msg.daterange", null, null)
                    + fomatter.format(finyear.getStartingDate()) + " - " + fomatter.format(finyear.getEndingDate())
                    + ", ";
        }
        if (contractorWiseAbstractReport.getElectionWardId() != null)
            queryParameters += messageSource.getMessage("msg.ward", null, null)
                    + boundaryService.getBoundaryById(contractorWiseAbstractReport.getElectionWardId()).getBoundaryNum()
                    + ", ";

        if (contractorWiseAbstractReport.getNatureOfWork() != null)
            queryParameters += " "+messageSource.getMessage("msg.natureofwork", null, null)
                    + natureOfWorkService.findById(contractorWiseAbstractReport.getNatureOfWork()).getName() + ", ";

        if (contractorWiseAbstractReport.getContractor() != null) {
            final List<Contractor> contractor = contractorService
                    .getContractorsByCodeOrName(contractorWiseAbstractReport.getContractor());
            if (contractor != null)
                queryParameters += " "+messageSource.getMessage("msg.contractor", null, null) + contractor.get(0).getName()
                        + "-" + contractor.get(0).getCode() + ", ";
        }

        if (contractorWiseAbstractReport.getWorkStatus() != null)
            queryParameters += " "+messageSource.getMessage("msg.workstatus", null, null)
                    + contractorWiseAbstractReport.getWorkStatus() + ", ";

        if (queryParameters.endsWith(", "))
            queryParameters = queryParameters.substring(0, queryParameters.length() - 2);

        reportParams.put("reportTitle", queryParameters);
        reportParams.put("dataRunDate", sdf.format(workProgressRegisterService.getReportSchedulerRunDate()));
        final HttpHeaders headers = new HttpHeaders();
        if (contentType.equalsIgnoreCase("pdf")) {
            reportInput.setReportFormat(FileFormat.PDF);
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            headers.add("content-disposition", "inline;filename=ContractorWiseAbstractReport.pdf");
        } else {
            reportInput.setReportFormat(FileFormat.XLS);
            headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
            headers.add("content-disposition", "inline;filename=ContractorWiseAbstractReport.xls");
        }
        reportOutput = reportService.createReport(reportInput);
        return new ResponseEntity<byte[]>(reportOutput.getReportOutputData(), headers, HttpStatus.CREATED);
    }
}
