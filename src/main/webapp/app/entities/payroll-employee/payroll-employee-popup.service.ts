import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PayrollEmployee } from './payroll-employee.model';
import { PayrollEmployeeService } from './payroll-employee.service';

@Injectable()
export class PayrollEmployeePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private payrollEmployeeService: PayrollEmployeeService

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
                this.payrollEmployeeService.find(id).subscribe((payrollEmployee) => {
                    if (payrollEmployee.createdDate) {
                        payrollEmployee.createdDate = {
                            year: payrollEmployee.createdDate.getFullYear(),
                            month: payrollEmployee.createdDate.getMonth() + 1,
                            day: payrollEmployee.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.payrollEmployeeModalRef(component, payrollEmployee);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.payrollEmployeeModalRef(component, new PayrollEmployee());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    payrollEmployeeModalRef(component: Component, payrollEmployee: PayrollEmployee): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.payrollEmployee = payrollEmployee;
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
