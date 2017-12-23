/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { AnnualReportsDialogComponent } from '../../../../../../main/webapp/app/entities/annual-reports/annual-reports-dialog.component';
import { AnnualReportsService } from '../../../../../../main/webapp/app/entities/annual-reports/annual-reports.service';
import { AnnualReports } from '../../../../../../main/webapp/app/entities/annual-reports/annual-reports.model';

describe('Component Tests', () => {

    describe('AnnualReports Management Dialog Component', () => {
        let comp: AnnualReportsDialogComponent;
        let fixture: ComponentFixture<AnnualReportsDialogComponent>;
        let service: AnnualReportsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [AnnualReportsDialogComponent],
                providers: [
                    AnnualReportsService
                ]
            })
            .overrideTemplate(AnnualReportsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnnualReportsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnnualReportsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AnnualReports(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.annualReports = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'annualReportsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AnnualReports();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.annualReports = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'annualReportsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
