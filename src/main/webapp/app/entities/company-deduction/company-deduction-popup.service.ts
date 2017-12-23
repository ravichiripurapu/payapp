import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyDeduction } from './company-deduction.model';
import { CompanyDeductionService } from './company-deduction.service';

@Injectable()
export class CompanyDeductionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private companyDeductionService: CompanyDeductionService

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
                this.companyDeductionService.find(id).subscribe((companyDeduction) => {
                    if (companyDeduction.createdDate) {
                        companyDeduction.createdDate = {
                            year: companyDeduction.createdDate.getFullYear(),
                            month: companyDeduction.createdDate.getMonth() + 1,
                            day: companyDeduction.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.companyDeductionModalRef(component, companyDeduction);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.companyDeductionModalRef(component, new CompanyDeduction());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    companyDeductionModalRef(component: Component, companyDeduction: CompanyDeduction): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyDeduction = companyDeduction;
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
