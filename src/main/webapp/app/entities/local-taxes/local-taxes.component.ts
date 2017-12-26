import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LocalTaxes } from './local-taxes.model';
import { LocalTaxesService } from './local-taxes.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-local-taxes',
    templateUrl: './local-taxes.component.html'
})
export class LocalTaxesComponent implements OnInit, OnDestroy {
localTaxes: LocalTaxes[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private localTaxesService: LocalTaxesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.localTaxesService.query().subscribe(
            (res: ResponseWrapper) => {
                this.localTaxes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLocalTaxes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LocalTaxes) {
        return item.id;
    }
    registerChangeInLocalTaxes() {
        this.eventSubscriber = this.eventManager.subscribe('localTaxesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
