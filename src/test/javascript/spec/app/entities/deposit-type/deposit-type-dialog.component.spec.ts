/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { DepositTypeDialogComponent } from '../../../../../../main/webapp/app/entities/deposit-type/deposit-type-dialog.component';
import { DepositTypeService } from '../../../../../../main/webapp/app/entities/deposit-type/deposit-type.service';
import { DepositType } from '../../../../../../main/webapp/app/entities/deposit-type/deposit-type.model';

describe('Component Tests', () => {

    describe('DepositType Management Dialog Component', () => {
        let comp: DepositTypeDialogComponent;
        let fixture: ComponentFixture<DepositTypeDialogComponent>;
        let service: DepositTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [DepositTypeDialogComponent],
                providers: [
                    DepositTypeService
                ]
            })
            .overrideTemplate(DepositTypeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DepositTypeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepositTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DepositType(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.depositType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'depositTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DepositType();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.depositType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'depositTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
