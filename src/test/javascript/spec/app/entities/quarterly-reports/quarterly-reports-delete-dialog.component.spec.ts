/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { QuarterlyReportsDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/quarterly-reports/quarterly-reports-delete-dialog.component';
import { QuarterlyReportsService } from '../../../../../../main/webapp/app/entities/quarterly-reports/quarterly-reports.service';

describe('Component Tests', () => {

    describe('QuarterlyReports Management Delete Component', () => {
        let comp: QuarterlyReportsDeleteDialogComponent;
        let fixture: ComponentFixture<QuarterlyReportsDeleteDialogComponent>;
        let service: QuarterlyReportsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [QuarterlyReportsDeleteDialogComponent],
                providers: [
                    QuarterlyReportsService
                ]
            })
            .overrideTemplate(QuarterlyReportsDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuarterlyReportsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuarterlyReportsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
