import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PayrollSummary } from './payroll-summary.model';
import { PayrollSummaryService } from './payroll-summary.service';

@Injectable()
export class PayrollSummaryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private payrollSummaryService: PayrollSummaryService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.payrollSummaryService.find(id).subscribe((payrollSummary) => {
                    if (payrollSummary.createdDate) {
                        payrollSummary.createdDate = {
                            year: payrollSummary.createdDate.getFullYear(),
                            month: payrollSummary.createdDate.getMonth() + 1,
                            day: payrollSummary.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.payrollSummaryModalRef(component, payrollSummary);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.payrollSummaryModalRef(component, new PayrollSummary());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    payrollSummaryModalRef(component: Component, payrollSummary: PayrollSummary): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.payrollSummary = payrollSummary;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
