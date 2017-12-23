import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DeductionType } from './deduction-type.model';
import { DeductionTypeService } from './deduction-type.service';

@Component({
    selector: 'jhi-deduction-type-detail',
    templateUrl: './deduction-type-detail.component.html'
})
export class DeductionTypeDetailComponent implements OnInit, OnDestroy {

    deductionType: DeductionType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private deductionTypeService: DeductionTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDeductionTypes();
    }

    load(id) {
        this.deductionTypeService.find(id).subscribe((deductionType) => {
            this.deductionType = deductionType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDeductionTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'deductionTypeListModification',
            (response) => this.load(this.deductionType.id)
        );
    }
}
