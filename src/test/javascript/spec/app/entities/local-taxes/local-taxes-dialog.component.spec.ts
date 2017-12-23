/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { LocalTaxesDialogComponent } from '../../../../../../main/webapp/app/entities/local-taxes/local-taxes-dialog.component';
import { LocalTaxesService } from '../../../../../../main/webapp/app/entities/local-taxes/local-taxes.service';
import { LocalTaxes } from '../../../../../../main/webapp/app/entities/local-taxes/local-taxes.model';

describe('Component Tests', () => {

    describe('LocalTaxes Management Dialog Component', () => {
        let comp: LocalTaxesDialogComponent;
        let fixture: ComponentFixture<LocalTaxesDialogComponent>;
        let service: LocalTaxesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [LocalTaxesDialogComponent],
                providers: [
                    LocalTaxesService
                ]
            })
            .overrideTemplate(LocalTaxesDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocalTaxesDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocalTaxesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LocalTaxes(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.localTaxes = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'localTaxesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LocalTaxes();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.localTaxes = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'localTaxesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
