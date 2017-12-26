/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { CompanyContactDialogComponent } from '../../../../../../main/webapp/app/entities/company-contact/company-contact-dialog.component';
import { CompanyContactService } from '../../../../../../main/webapp/app/entities/company-contact/company-contact.service';
import { CompanyContact } from '../../../../../../main/webapp/app/entities/company-contact/company-contact.model';

describe('Component Tests', () => {

    describe('CompanyContact Management Dialog Component', () => {
        let comp: CompanyContactDialogComponent;
        let fixture: ComponentFixture<CompanyContactDialogComponent>;
        let service: CompanyContactService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyContactDialogComponent],
                providers: [
                    CompanyContactService
                ]
            })
            .overrideTemplate(CompanyContactDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyContactDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyContactService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyContact(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.companyContact = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyContactListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyContact();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.companyContact = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyContactListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
