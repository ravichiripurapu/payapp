/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { DepositFrequencyDialogComponent } from '../../../../../../main/webapp/app/entities/deposit-frequency/deposit-frequency-dialog.component';
import { DepositFrequencyService } from '../../../../../../main/webapp/app/entities/deposit-frequency/deposit-frequency.service';
import { DepositFrequency } from '../../../../../../main/webapp/app/entities/deposit-frequency/deposit-frequency.model';

describe('Component Tests', () => {

    describe('DepositFrequency Management Dialog Component', () => {
        let comp: DepositFrequencyDialogComponent;
        let fixture: ComponentFixture<DepositFrequencyDialogComponent>;
        let service: DepositFrequencyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [DepositFrequencyDialogComponent],
                providers: [
                    DepositFrequencyService
                ]
            })
            .overrideTemplate(DepositFrequencyDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DepositFrequencyDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepositFrequencyService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DepositFrequency(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.depositFrequency = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'depositFrequencyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DepositFrequency();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.depositFrequency = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'depositFrequencyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
