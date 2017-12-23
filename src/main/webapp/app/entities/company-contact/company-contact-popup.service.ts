import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyContact } from './company-contact.model';
import { CompanyContactService } from './company-contact.service';

@Injectable()
export class CompanyContactPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private companyContactService: CompanyContactService

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
                this.companyContactService.find(id).subscribe((companyContact) => {
                    if (companyContact.dob) {
                        companyContact.dob = {
                            year: companyContact.dob.getFullYear(),
                            month: companyContact.dob.getMonth() + 1,
                            day: companyContact.dob.getDate()
                        };
                    }
                    if (companyContact.createdDate) {
                        companyContact.createdDate = {
                            year: companyContact.createdDate.getFullYear(),
                            month: companyContact.createdDate.getMonth() + 1,
                            day: companyContact.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.companyContactModalRef(component, companyContact);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.companyContactModalRef(component, new CompanyContact());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    companyContactModalRef(component: Component, companyContact: CompanyContact): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyContact = companyContact;
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
