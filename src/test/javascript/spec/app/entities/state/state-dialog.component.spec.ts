/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { StateDialogComponent } from '../../../../../../main/webapp/app/entities/state/state-dialog.component';
import { StateService } from '../../../../../../main/webapp/app/entities/state/state.service';
import { State } from '../../../../../../main/webapp/app/entities/state/state.model';

describe('Component Tests', () => {

    describe('State Management Dialog Component', () => {
        let comp: StateDialogComponent;
        let fixture: ComponentFixture<StateDialogComponent>;
        let service: StateService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [StateDialogComponent],
                providers: [
                    StateService
                ]
            })
            .overrideTemplate(StateDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StateDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StateService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new State(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.state = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'stateListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new State();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.state = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'stateListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
