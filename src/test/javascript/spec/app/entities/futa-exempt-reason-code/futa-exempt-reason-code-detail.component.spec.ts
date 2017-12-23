/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { FutaExemptReasonCodeDetailComponent } from '../../../../../../main/webapp/app/entities/futa-exempt-reason-code/futa-exempt-reason-code-detail.component';
import { FutaExemptReasonCodeService } from '../../../../../../main/webapp/app/entities/futa-exempt-reason-code/futa-exempt-reason-code.service';
import { FutaExemptReasonCode } from '../../../../../../main/webapp/app/entities/futa-exempt-reason-code/futa-exempt-reason-code.model';

describe('Component Tests', () => {

    describe('FutaExemptReasonCode Management Detail Component', () => {
        let comp: FutaExemptReasonCodeDetailComponent;
        let fixture: ComponentFixture<FutaExemptReasonCodeDetailComponent>;
        let service: FutaExemptReasonCodeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [FutaExemptReasonCodeDetailComponent],
                providers: [
                    FutaExemptReasonCodeService
                ]
            })
            .overrideTemplate(FutaExemptReasonCodeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FutaExemptReasonCodeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FutaExemptReasonCodeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new FutaExemptReasonCode(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.futaExemptReasonCode).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
