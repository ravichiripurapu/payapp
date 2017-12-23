import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Employee } from './employee.model';
import { EmployeeService } from './employee.service';

@Injectable()
export class EmployeePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private employeeService: EmployeeService

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
                this.employeeService.find(id).subscribe((employee) => {
                    if (employee.dob) {
                        employee.dob = {
                            year: employee.dob.getFullYear(),
                            month: employee.dob.getMonth() + 1,
                            day: employee.dob.getDate()
                        };
                    }
                    if (employee.hireDate) {
                        employee.hireDate = {
                            year: employee.hireDate.getFullYear(),
                            month: employee.hireDate.getMonth() + 1,
                            day: employee.hireDate.getDate()
                        };
                    }
                    if (employee.createdDate) {
                        employee.createdDate = {
                            year: employee.createdDate.getFullYear(),
                            month: employee.createdDate.getMonth() + 1,
                            day: employee.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.employeeModalRef(component, employee);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.employeeModalRef(component, new Employee());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    employeeModalRef(component: Component, employee: Employee): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.employee = employee;
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
