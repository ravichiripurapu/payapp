import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AnnualReports } from './annual-reports.model';
import { AnnualReportsService } from './annual-reports.service';

@Injectable()
export class AnnualReportsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private annualReportsService: AnnualReportsService

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
                this.annualReportsService.find(id).subscribe((annualReports) => {
                    if (annualReports.createdDate) {
                        annualReports.createdDate = {
                            year: annualReports.createdDate.getFullYear(),
                            month: annualReports.createdDate.getMonth() + 1,
                            day: annualReports.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.annualReportsModalRef(component, annualReports);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.annualReportsModalRef(component, new AnnualReports());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    annualReportsModalRef(component: Component, annualReports: AnnualReports): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.annualReports = annualReports;
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
