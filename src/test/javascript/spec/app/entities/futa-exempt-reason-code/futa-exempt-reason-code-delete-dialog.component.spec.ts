/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { FutaExemptReasonCodeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/futa-exempt-reason-code/futa-exempt-reason-code-delete-dialog.component';
import { FutaExemptReasonCodeService } from '../../../../../../main/webapp/app/entities/futa-exempt-reason-code/futa-exempt-reason-code.service';

describe('Component Tests', () => {

    describe('FutaExemptReasonCode Management Delete Component', () => {
        let comp: FutaExemptReasonCodeDeleteDialogComponent;
        let fixture: ComponentFixture<FutaExemptReasonCodeDeleteDialogComponent>;
        let service: FutaExemptReasonCodeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [FutaExemptReasonCodeDeleteDialogComponent],
                providers: [
                    FutaExemptReasonCodeService
                ]
            })
            .overrideTemplate(FutaExemptReasonCodeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FutaExemptReasonCodeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FutaExemptReasonCodeService);
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
