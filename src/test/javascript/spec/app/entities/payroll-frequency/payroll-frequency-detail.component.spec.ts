/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { PayrollFrequencyDetailComponent } from '../../../../../../main/webapp/app/entities/payroll-frequency/payroll-frequency-detail.component';
import { PayrollFrequencyService } from '../../../../../../main/webapp/app/entities/payroll-frequency/payroll-frequency.service';
import { PayrollFrequency } from '../../../../../../main/webapp/app/entities/payroll-frequency/payroll-frequency.model';

describe('Component Tests', () => {

    describe('PayrollFrequency Management Detail Component', () => {
        let comp: PayrollFrequencyDetailComponent;
        let fixture: ComponentFixture<PayrollFrequencyDetailComponent>;
        let service: PayrollFrequencyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollFrequencyDetailComponent],
                providers: [
                    PayrollFrequencyService
                ]
            })
            .overrideTemplate(PayrollFrequencyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollFrequencyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollFrequencyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PayrollFrequency(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.payrollFrequency).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
