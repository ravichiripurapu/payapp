import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EmployeeBank } from './employee-bank.model';
import { EmployeeBankService } from './employee-bank.service';

@Injectable()
export class EmployeeBankPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private employeeBankService: EmployeeBankService

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
                this.employeeBankService.find(id).subscribe((employeeBank) => {
                    if (employeeBank.createdDate) {
                        employeeBank.createdDate = {
                            year: employeeBank.createdDate.getFullYear(),
                            month: employeeBank.createdDate.getMonth() + 1,
                            day: employeeBank.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.employeeBankModalRef(component, employeeBank);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.employeeBankModalRef(component, new EmployeeBank());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    employeeBankModalRef(component: Component, employeeBank: EmployeeBank): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.employeeBank = employeeBank;
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
