import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EmployeeWorkLocation } from './employee-work-location.model';
import { EmployeeWorkLocationService } from './employee-work-location.service';

@Injectable()
export class EmployeeWorkLocationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private employeeWorkLocationService: EmployeeWorkLocationService

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
                this.employeeWorkLocationService.find(id).subscribe((employeeWorkLocation) => {
                    if (employeeWorkLocation.createdDate) {
                        employeeWorkLocation.createdDate = {
                            year: employeeWorkLocation.createdDate.getFullYear(),
                            month: employeeWorkLocation.createdDate.getMonth() + 1,
                            day: employeeWorkLocation.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.employeeWorkLocationModalRef(component, employeeWorkLocation);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.employeeWorkLocationModalRef(component, new EmployeeWorkLocation());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    employeeWorkLocationModalRef(component: Component, employeeWorkLocation: EmployeeWorkLocation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.employeeWorkLocation = employeeWorkLocation;
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
