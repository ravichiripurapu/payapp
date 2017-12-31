import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PayrollEarnings } from './payroll-earnings.model';
import { PayrollEarningsService } from './payroll-earnings.service';

@Injectable()
export class PayrollEarningsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private payrollEarningsService: PayrollEarningsService

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
                this.payrollEarningsService.find(id).subscribe((employeePayEarning) => {
                    if (employeePayEarning.createdDate) {
                        employeePayEarning.createdDate = {
                            year: employeePayEarning.createdDate.getFullYear(),
                            month: employeePayEarning.createdDate.getMonth() + 1,
                            day: employeePayEarning.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.payrollEarningsModalRef(component, employeePayEarning);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.payrollEarningsModalRef(component, new PayrollEarnings());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    payrollEarningsModalRef(component: Component, employeePayEarning: PayrollEarnings): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.employeePayEarning = employeePayEarning;
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
