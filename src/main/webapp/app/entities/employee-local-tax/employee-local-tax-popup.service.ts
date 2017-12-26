import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EmployeeLocalTax } from './employee-local-tax.model';
import { EmployeeLocalTaxService } from './employee-local-tax.service';

@Injectable()
export class EmployeeLocalTaxPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private employeeLocalTaxService: EmployeeLocalTaxService

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
                this.employeeLocalTaxService.find(id).subscribe((employeeLocalTax) => {
                    if (employeeLocalTax.createdDate) {
                        employeeLocalTax.createdDate = {
                            year: employeeLocalTax.createdDate.getFullYear(),
                            month: employeeLocalTax.createdDate.getMonth() + 1,
                            day: employeeLocalTax.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.employeeLocalTaxModalRef(component, employeeLocalTax);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.employeeLocalTaxModalRef(component, new EmployeeLocalTax());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    employeeLocalTaxModalRef(component: Component, employeeLocalTax: EmployeeLocalTax): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.employeeLocalTax = employeeLocalTax;
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
