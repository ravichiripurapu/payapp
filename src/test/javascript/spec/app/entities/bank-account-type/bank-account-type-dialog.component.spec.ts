/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { BankAccountTypeDialogComponent } from '../../../../../../main/webapp/app/entities/bank-account-type/bank-account-type-dialog.component';
import { BankAccountTypeService } from '../../../../../../main/webapp/app/entities/bank-account-type/bank-account-type.service';
import { BankAccountType } from '../../../../../../main/webapp/app/entities/bank-account-type/bank-account-type.model';

describe('Component Tests', () => {

    describe('BankAccountType Management Dialog Component', () => {
        let comp: BankAccountTypeDialogComponent;
        let fixture: ComponentFixture<BankAccountTypeDialogComponent>;
        let service: BankAccountTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [BankAccountTypeDialogComponent],
                providers: [
                    BankAccountTypeService
                ]
            })
            .overrideTemplate(BankAccountTypeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BankAccountTypeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BankAccountTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BankAccountType(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.bankAccountType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bankAccountTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BankAccountType();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.bankAccountType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bankAccountTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
