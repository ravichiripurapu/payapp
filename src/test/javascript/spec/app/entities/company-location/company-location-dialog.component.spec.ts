/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { CompanyLocationDialogComponent } from '../../../../../../main/webapp/app/entities/company-location/company-location-dialog.component';
import { CompanyLocationService } from '../../../../../../main/webapp/app/entities/company-location/company-location.service';
import { CompanyLocation } from '../../../../../../main/webapp/app/entities/company-location/company-location.model';

describe('Component Tests', () => {

    describe('CompanyLocation Management Dialog Component', () => {
        let comp: CompanyLocationDialogComponent;
        let fixture: ComponentFixture<CompanyLocationDialogComponent>;
        let service: CompanyLocationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyLocationDialogComponent],
                providers: [
                    CompanyLocationService
                ]
            })
            .overrideTemplate(CompanyLocationDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyLocationDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyLocationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyLocation(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.companyLocation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyLocationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompanyLocation();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.companyLocation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'companyLocationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
