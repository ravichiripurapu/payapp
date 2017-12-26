import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DepositType } from './deposit-type.model';
import { DepositTypeService } from './deposit-type.service';

@Component({
    selector: 'jhi-deposit-type-detail',
    templateUrl: './deposit-type-detail.component.html'
})
export class DepositTypeDetailComponent implements OnInit, OnDestroy {

    depositType: DepositType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private depositTypeService: DepositTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDepositTypes();
    }

    load(id) {
        this.depositTypeService.find(id).subscribe((depositType) => {
            this.depositType = depositType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDepositTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'depositTypeListModification',
            (response) => this.load(this.depositType.id)
        );
    }
}
