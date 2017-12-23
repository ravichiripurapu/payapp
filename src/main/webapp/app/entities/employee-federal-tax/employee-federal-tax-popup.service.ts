import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EmployeeFederalTax } from './employee-federal-tax.model';
import { EmployeeFederalTaxService } from './employee-federal-tax.service';

@Injectable()
export class EmployeeFederalTaxPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private employeeFederalTaxService: EmployeeFederalTaxService

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
                this.employeeFederalTaxService.find(id).subscribe((employeeFederalTax) => {
                    if (employeeFederalTax.createdDate) {
                        employeeFederalTax.createdDate = {
                            year: employeeFederalTax.createdDate.getFullYear(),
                            month: employeeFederalTax.createdDate.getMonth() + 1,
                            day: employeeFederalTax.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.employeeFederalTaxModalRef(component, employeeFederalTax);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.employeeFederalTaxModalRef(component, new EmployeeFederalTax());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    employeeFederalTaxModalRef(component: Component, employeeFederalTax: EmployeeFederalTax): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.employeeFederalTax = employeeFederalTax;
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
