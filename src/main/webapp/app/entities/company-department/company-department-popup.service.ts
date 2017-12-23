import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyDepartment } from './company-department.model';
import { CompanyDepartmentService } from './company-department.service';

@Injectable()
export class CompanyDepartmentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private companyDepartmentService: CompanyDepartmentService

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
                this.companyDepartmentService.find(id).subscribe((companyDepartment) => {
                    if (companyDepartment.createdDate) {
                        companyDepartment.createdDate = {
                            year: companyDepartment.createdDate.getFullYear(),
                            month: companyDepartment.createdDate.getMonth() + 1,
                            day: companyDepartment.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.companyDepartmentModalRef(component, companyDepartment);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.companyDepartmentModalRef(component, new CompanyDepartment());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    companyDepartmentModalRef(component: Component, companyDepartment: CompanyDepartment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyDepartment = companyDepartment;
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
