import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PayrollEmployeeTaxes } from './payroll-employee-taxes.model';
import { PayrollEmployeeTaxesService } from './payroll-employee-taxes.service';

@Injectable()
export class PayrollEmployeeTaxesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private payrollEmployeeTaxesService: PayrollEmployeeTaxesService

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
                this.payrollEmployeeTaxesService.find(id).subscribe((payrollEmployeeTaxes) => {
                    if (payrollEmployeeTaxes.createdDate) {
                        payrollEmployeeTaxes.createdDate = {
                            year: payrollEmployeeTaxes.createdDate.getFullYear(),
                            month: payrollEmployeeTaxes.createdDate.getMonth() + 1,
                            day: payrollEmployeeTaxes.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.payrollEmployeeTaxesModalRef(component, payrollEmployeeTaxes);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.payrollEmployeeTaxesModalRef(component, new PayrollEmployeeTaxes());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    payrollEmployeeTaxesModalRef(component: Component, payrollEmployeeTaxes: PayrollEmployeeTaxes): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.payrollEmployeeTaxes = payrollEmployeeTaxes;
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
