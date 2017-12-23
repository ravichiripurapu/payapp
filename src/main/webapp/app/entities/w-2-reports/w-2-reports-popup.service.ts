import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { W2Reports } from './w-2-reports.model';
import { W2ReportsService } from './w-2-reports.service';

@Injectable()
export class W2ReportsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private w2ReportsService: W2ReportsService

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
                this.w2ReportsService.find(id).subscribe((w2Reports) => {
                    if (w2Reports.createdDate) {
                        w2Reports.createdDate = {
                            year: w2Reports.createdDate.getFullYear(),
                            month: w2Reports.createdDate.getMonth() + 1,
                            day: w2Reports.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.w2ReportsModalRef(component, w2Reports);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.w2ReportsModalRef(component, new W2Reports());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    w2ReportsModalRef(component: Component, w2Reports: W2Reports): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.w2Reports = w2Reports;
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
