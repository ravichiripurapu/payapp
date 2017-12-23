import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyCompensation } from './company-compensation.model';
import { CompanyCompensationService } from './company-compensation.service';

@Injectable()
export class CompanyCompensationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private companyCompensationService: CompanyCompensationService

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
                this.companyCompensationService.find(id).subscribe((companyCompensation) => {
                    if (companyCompensation.createdDate) {
                        companyCompensation.createdDate = {
                            year: companyCompensation.createdDate.getFullYear(),
                            month: companyCompensation.createdDate.getMonth() + 1,
                            day: companyCompensation.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.companyCompensationModalRef(component, companyCompensation);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.companyCompensationModalRef(component, new CompanyCompensation());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    companyCompensationModalRef(component: Component, companyCompensation: CompanyCompensation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyCompensation = companyCompensation;
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
