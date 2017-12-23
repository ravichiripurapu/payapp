import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { QuarterlyReports } from './quarterly-reports.model';
import { QuarterlyReportsService } from './quarterly-reports.service';

@Injectable()
export class QuarterlyReportsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private quarterlyReportsService: QuarterlyReportsService

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
                this.quarterlyReportsService.find(id).subscribe((quarterlyReports) => {
                    if (quarterlyReports.createdDate) {
                        quarterlyReports.createdDate = {
                            year: quarterlyReports.createdDate.getFullYear(),
                            month: quarterlyReports.createdDate.getMonth() + 1,
                            day: quarterlyReports.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.quarterlyReportsModalRef(component, quarterlyReports);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.quarterlyReportsModalRef(component, new QuarterlyReports());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    quarterlyReportsModalRef(component: Component, quarterlyReports: QuarterlyReports): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.quarterlyReports = quarterlyReports;
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
