/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { PayrollSummaryDetailComponent } from '../../../../../../main/webapp/app/entities/payroll-summary/payroll-summary-detail.component';
import { PayrollSummaryService } from '../../../../../../main/webapp/app/entities/payroll-summary/payroll-summary.service';
import { PayrollSummary } from '../../../../../../main/webapp/app/entities/payroll-summary/payroll-summary.model';

describe('Component Tests', () => {

    describe('PayrollSummary Management Detail Component', () => {
        let comp: PayrollSummaryDetailComponent;
        let fixture: ComponentFixture<PayrollSummaryDetailComponent>;
        let service: PayrollSummaryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollSummaryDetailComponent],
                providers: [
                    PayrollSummaryService
                ]
            })
            .overrideTemplate(PayrollSummaryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollSummaryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollSummaryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PayrollSummary(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.payrollSummary).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
