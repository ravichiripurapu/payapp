import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyStateTax } from './company-state-tax.model';
import { CompanyStateTaxService } from './company-state-tax.service';

@Injectable()
export class CompanyStateTaxPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private companyStateTaxService: CompanyStateTaxService

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
                this.companyStateTaxService.find(id).subscribe((companyStateTax) => {
                    if (companyStateTax.effectiveDate) {
                        companyStateTax.effectiveDate = {
                            year: companyStateTax.effectiveDate.getFullYear(),
                            month: companyStateTax.effectiveDate.getMonth() + 1,
                            day: companyStateTax.effectiveDate.getDate()
                        };
                    }
                    if (companyStateTax.createdDate) {
                        companyStateTax.createdDate = {
                            year: companyStateTax.createdDate.getFullYear(),
                            month: companyStateTax.createdDate.getMonth() + 1,
                            day: companyStateTax.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.companyStateTaxModalRef(component, companyStateTax);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.companyStateTaxModalRef(component, new CompanyStateTax());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    companyStateTaxModalRef(component: Component, companyStateTax: CompanyStateTax): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyStateTax = companyStateTax;
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
