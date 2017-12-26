import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyLocalTax } from './company-local-tax.model';
import { CompanyLocalTaxService } from './company-local-tax.service';

@Injectable()
export class CompanyLocalTaxPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private companyLocalTaxService: CompanyLocalTaxService

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
                this.companyLocalTaxService.find(id).subscribe((companyLocalTax) => {
                    if (companyLocalTax.createdDate) {
                        companyLocalTax.createdDate = {
                            year: companyLocalTax.createdDate.getFullYear(),
                            month: companyLocalTax.createdDate.getMonth() + 1,
                            day: companyLocalTax.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.companyLocalTaxModalRef(component, companyLocalTax);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.companyLocalTaxModalRef(component, new CompanyLocalTax());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    companyLocalTaxModalRef(component: Component, companyLocalTax: CompanyLocalTax): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyLocalTax = companyLocalTax;
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
