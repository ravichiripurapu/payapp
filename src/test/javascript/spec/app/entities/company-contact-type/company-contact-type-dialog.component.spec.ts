/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { CompanyContactTypeDialogComponent } from '../../../../../../main/webapp/app/entities/company-contact-type/company-contact-type-dialog.component';
import { CompanyContactTypeService } from '../../../../../../main/webapp/app/entities/company-contact-type/company-contact-type.service';
import { CompanyContactType } from '../../../../../../main/webapp/app/entities/company-contact-type/company-contact-type.model';

describe('Component Tests', () => {

    describe('CompanyContactType Management Dialog Component', () => {
        let comp: CompanyContactTypeDialogComponent;
        let fixture: ComponentFixture<CompanyContactTypeDialogComponent>;
        let service: CompanyContactTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyContactTypeDialogComponent],
                providers: [
                    CompanyContactTypeService
                ]
            })
            .overrideTemplate(CompanyContactTypeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyContactTypeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyContactTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyContactType(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.companyContactType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyContactTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyContactType();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.companyContactType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyContactTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
