import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FutaExemptReasonCode } from './futa-exempt-reason-code.model';
import { FutaExemptReasonCodeService } from './futa-exempt-reason-code.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-futa-exempt-reason-code',
    templateUrl: './futa-exempt-reason-code.component.html'
})
export class FutaExemptReasonCodeComponent implements OnInit, OnDestroy {
futaExemptReasonCodes: FutaExemptReasonCode[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private futaExemptReasonCodeService: FutaExemptReasonCodeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.futaExemptReasonCodeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.futaExemptReasonCodes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFutaExemptReasonCodes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FutaExemptReasonCode) {
        return item.id;
    }
    registerChangeInFutaExemptReasonCodes() {
        this.eventSubscriber = this.eventManager.subscribe('futaExemptReasonCodeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
