/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { QuarterlyReportsDialogComponent } from '../../../../../../main/webapp/app/entities/quarterly-reports/quarterly-reports-dialog.component';
import { QuarterlyReportsService } from '../../../../../../main/webapp/app/entities/quarterly-reports/quarterly-reports.service';
import { QuarterlyReports } from '../../../../../../main/webapp/app/entities/quarterly-reports/quarterly-reports.model';

describe('Component Tests', () => {

    describe('QuarterlyReports Management Dialog Component', () => {
        let comp: QuarterlyReportsDialogComponent;
        let fixture: ComponentFixture<QuarterlyReportsDialogComponent>;
        let service: QuarterlyReportsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [QuarterlyReportsDialogComponent],
                providers: [
                    QuarterlyReportsService
                ]
            })
            .overrideTemplate(QuarterlyReportsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuarterlyReportsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuarterlyReportsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new QuarterlyReports(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.quarterlyReports = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'quarterlyReportsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new QuarterlyReports();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.quarterlyReports = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'quarterlyReportsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
