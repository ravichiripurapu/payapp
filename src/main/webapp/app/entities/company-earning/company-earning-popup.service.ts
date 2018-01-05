import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyEarning } from './company-earning.model';
import { CompanyEarningService } from './company-earning.service';

@Injectable()
export class CompanyEarningPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private companyEarningService: CompanyEarningService

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
                this.companyEarningService.find(id).subscribe((companyEarningType) => {
                    if (companyEarningType.createdDate) {
                        companyEarningType.createdDate = {
                            year: companyEarningType.createdDate.getFullYear(),
                            month: companyEarningType.createdDate.getMonth() + 1,
                            day: companyEarningType.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.companyEarningModalRef(component, companyEarningType);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.companyEarningModalRef(component, new CompanyEarning());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    companyEarningModalRef(component: Component, companyEarningType: CompanyEarning): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyEarningType = companyEarningType;
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
