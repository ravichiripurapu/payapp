import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PayrollSchedule } from './payroll-schedule.model';
import { PayrollScheduleService } from './payroll-schedule.service';

@Injectable()
export class PayrollSchedulePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private payrollScheduleService: PayrollScheduleService

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
                this.payrollScheduleService.find(id).subscribe((payrollSchedule) => {
                    if (payrollSchedule.checkDate) {
                        payrollSchedule.checkDate = {
                            year: payrollSchedule.checkDate.getFullYear(),
                            month: payrollSchedule.checkDate.getMonth() + 1,
                            day: payrollSchedule.checkDate.getDate()
                        };
                    }
                    if (payrollSchedule.periodEnd) {
                        payrollSchedule.periodEnd = {
                            year: payrollSchedule.periodEnd.getFullYear(),
                            month: payrollSchedule.periodEnd.getMonth() + 1,
                            day: payrollSchedule.periodEnd.getDate()
                        };
                    }
                    if (payrollSchedule.periodStart) {
                        payrollSchedule.periodStart = {
                            year: payrollSchedule.periodStart.getFullYear(),
                            month: payrollSchedule.periodStart.getMonth() + 1,
                            day: payrollSchedule.periodStart.getDate()
                        };
                    }
                    if (payrollSchedule.approveDate) {
                        payrollSchedule.approveDate = {
                            year: payrollSchedule.approveDate.getFullYear(),
                            month: payrollSchedule.approveDate.getMonth() + 1,
                            day: payrollSchedule.approveDate.getDate()
                        };
                    }
                    if (payrollSchedule.createdDate) {
                        payrollSchedule.createdDate = {
                            year: payrollSchedule.createdDate.getFullYear(),
                            month: payrollSchedule.createdDate.getMonth() + 1,
                            day: payrollSchedule.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.payrollScheduleModalRef(component, payrollSchedule);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.payrollScheduleModalRef(component, new PayrollSchedule());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    payrollScheduleModalRef(component: Component, payrollSchedule: PayrollSchedule): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.payrollSchedule = payrollSchedule;
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
