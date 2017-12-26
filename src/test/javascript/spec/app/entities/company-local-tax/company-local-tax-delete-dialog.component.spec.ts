/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { CompanyLocalTaxDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/company-local-tax/company-local-tax-delete-dialog.component';
import { CompanyLocalTaxService } from '../../../../../../main/webapp/app/entities/company-local-tax/company-local-tax.service';

describe('Component Tests', () => {

    describe('CompanyLocalTax Management Delete Component', () => {
        let comp: CompanyLocalTaxDeleteDialogComponent;
        let fixture: ComponentFixture<CompanyLocalTaxDeleteDialogComponent>;
        let service: CompanyLocalTaxService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyLocalTaxDeleteDialogComponent],
                providers: [
                    CompanyLocalTaxService
                ]
            })
            .overrideTemplate(CompanyLocalTaxDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyLocalTaxDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyLocalTaxService);
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
