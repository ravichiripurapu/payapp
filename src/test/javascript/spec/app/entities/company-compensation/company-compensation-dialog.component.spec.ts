/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { CompanyCompensationDialogComponent } from '../../../../../../main/webapp/app/entities/company-compensation/company-compensation-dialog.component';
import { CompanyCompensationService } from '../../../../../../main/webapp/app/entities/company-compensation/company-compensation.service';
import { CompanyCompensation } from '../../../../../../main/webapp/app/entities/company-compensation/company-compensation.model';

describe('Component Tests', () => {

    describe('CompanyCompensation Management Dialog Component', () => {
        let comp: CompanyCompensationDialogComponent;
        let fixture: ComponentFixture<CompanyCompensationDialogComponent>;
        let service: CompanyCompensationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyCompensationDialogComponent],
                providers: [
                    CompanyCompensationService
                ]
            })
            .overrideTemplate(CompanyCompensationDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyCompensationDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyCompensationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyCompensation(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.companyCompensation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyCompensationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyCompensation();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.companyCompensation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyCompensationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
