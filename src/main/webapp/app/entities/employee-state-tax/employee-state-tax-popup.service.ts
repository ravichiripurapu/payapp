import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EmployeeStateTax } from './employee-state-tax.model';
import { EmployeeStateTaxService } from './employee-state-tax.service';

@Injectable()
export class EmployeeStateTaxPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private employeeStateTaxService: EmployeeStateTaxService

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
                this.employeeStateTaxService.find(id).subscribe((employeeStateTax) => {
                    if (employeeStateTax.createdDate) {
                        employeeStateTax.createdDate = {
                            year: employeeStateTax.createdDate.getFullYear(),
                            month: employeeStateTax.createdDate.getMonth() + 1,
                            day: employeeStateTax.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.employeeStateTaxModalRef(component, employeeStateTax);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.employeeStateTaxModalRef(component, new EmployeeStateTax());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    employeeStateTaxModalRef(component: Component, employeeStateTax: EmployeeStateTax): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.employeeStateTax = employeeStateTax;
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
