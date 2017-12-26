import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DeductionSubType } from './deduction-sub-type.model';
import { DeductionSubTypeService } from './deduction-sub-type.service';

@Component({
    selector: 'jhi-deduction-sub-type-detail',
    templateUrl: './deduction-sub-type-detail.component.html'
})
export class DeductionSubTypeDetailComponent implements OnInit, OnDestroy {

    deductionSubType: DeductionSubType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private deductionSubTypeService: DeductionSubTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDeductionSubTypes();
    }

    load(id) {
        this.deductionSubTypeService.find(id).subscribe((deductionSubType) => {
            this.deductionSubType = deductionSubType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDeductionSubTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'deductionSubTypeListModification',
            (response) => this.load(this.deductionSubType.id)
        );
    }
}
