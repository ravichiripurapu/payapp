import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FutaExemptReasonCode } from './futa-exempt-reason-code.model';
import { FutaExemptReasonCodeService } from './futa-exempt-reason-code.service';

@Injectable()
export class FutaExemptReasonCodePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private futaExemptReasonCodeService: FutaExemptReasonCodeService

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
                this.futaExemptReasonCodeService.find(id).subscribe((futaExemptReasonCode) => {
                    this.ngbModalRef = this.futaExemptReasonCodeModalRef(component, futaExemptReasonCode);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.futaExemptReasonCodeModalRef(component, new FutaExemptReasonCode());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    futaExemptReasonCodeModalRef(component: Component, futaExemptReasonCode: FutaExemptReasonCode): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.futaExemptReasonCode = futaExemptReasonCode;
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
