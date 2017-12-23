import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyLocation } from './company-location.model';
import { CompanyLocationService } from './company-location.service';

@Injectable()
export class CompanyLocationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private companyLocationService: CompanyLocationService

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
                this.companyLocationService.find(id).subscribe((companyLocation) => {
                    if (companyLocation.createdDate) {
                        companyLocation.createdDate = {
                            year: companyLocation.createdDate.getFullYear(),
                            month: companyLocation.createdDate.getMonth() + 1,
                            day: companyLocation.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.companyLocationModalRef(component, companyLocation);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.companyLocationModalRef(component, new CompanyLocation());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    companyLocationModalRef(component: Component, companyLocation: CompanyLocation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyLocation = companyLocation;
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
