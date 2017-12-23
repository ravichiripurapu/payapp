import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyBank } from './company-bank.model';
import { CompanyBankService } from './company-bank.service';

@Injectable()
export class CompanyBankPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private companyBankService: CompanyBankService

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
                this.companyBankService.find(id).subscribe((companyBank) => {
                    if (companyBank.createdDate) {
                        companyBank.createdDate = {
                            year: companyBank.createdDate.getFullYear(),
                            month: companyBank.createdDate.getMonth() + 1,
                            day: companyBank.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.companyBankModalRef(component, companyBank);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.companyBankModalRef(component, new CompanyBank());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    companyBankModalRef(component: Component, companyBank: CompanyBank): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyBank = companyBank;
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
