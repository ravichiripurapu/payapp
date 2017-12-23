import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EmployeeResidenceLocation } from './employee-residence-location.model';
import { EmployeeResidenceLocationService } from './employee-residence-location.service';

@Injectable()
export class EmployeeResidenceLocationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private employeeResidenceLocationService: EmployeeResidenceLocationService

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
                this.employeeResidenceLocationService.find(id).subscribe((employeeResidenceLocation) => {
                    if (employeeResidenceLocation.createdDate) {
                        employeeResidenceLocation.createdDate = {
                            year: employeeResidenceLocation.createdDate.getFullYear(),
                            month: employeeResidenceLocation.createdDate.getMonth() + 1,
                            day: employeeResidenceLocation.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.employeeResidenceLocationModalRef(component, employeeResidenceLocation);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.employeeResidenceLocationModalRef(component, new EmployeeResidenceLocation());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    employeeResidenceLocationModalRef(component: Component, employeeResidenceLocation: EmployeeResidenceLocation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.employeeResidenceLocation = employeeResidenceLocation;
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
