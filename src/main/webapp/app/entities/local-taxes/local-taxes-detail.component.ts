import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LocalTaxes } from './local-taxes.model';
import { LocalTaxesService } from './local-taxes.service';

@Component({
    selector: 'jhi-local-taxes-detail',
    templateUrl: './local-taxes-detail.component.html'
})
export class LocalTaxesDetailComponent implements OnInit, OnDestroy {

    localTaxes: LocalTaxes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private localTaxesService: LocalTaxesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLocalTaxes();
    }

    load(id) {
        this.localTaxesService.find(id).subscribe((localTaxes) => {
            this.localTaxes = localTaxes;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLocalTaxes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'localTaxesListModification',
            (response) => this.load(this.localTaxes.id)
        );
    }
}
