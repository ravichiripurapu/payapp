import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PayrollEmployerTaxes } from './payroll-employer-taxes.model';
import { PayrollEmployerTaxesService } from './payroll-employer-taxes.service';

@Injectable()
export class PayrollEmployerTaxesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private payrollEmployerTaxesService: PayrollEmployerTaxesService

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
                this.payrollEmployerTaxesService.find(id).subscribe((payrollEmployerTaxes) => {
                    if (payrollEmployerTaxes.createdDate) {
                        payrollEmployerTaxes.createdDate = {
                            year: payrollEmployerTaxes.createdDate.getFullYear(),
                            month: payrollEmployerTaxes.createdDate.getMonth() + 1,
                            day: payrollEmployerTaxes.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.payrollEmployerTaxesModalRef(component, payrollEmployerTaxes);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.payrollEmployerTaxesModalRef(component, new PayrollEmployerTaxes());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    payrollEmployerTaxesModalRef(component: Component, payrollEmployerTaxes: PayrollEmployerTaxes): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.payrollEmployerTaxes = payrollEmployerTaxes;
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
