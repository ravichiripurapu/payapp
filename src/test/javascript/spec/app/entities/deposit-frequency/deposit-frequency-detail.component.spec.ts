/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { DepositFrequencyDetailComponent } from '../../../../../../main/webapp/app/entities/deposit-frequency/deposit-frequency-detail.component';
import { DepositFrequencyService } from '../../../../../../main/webapp/app/entities/deposit-frequency/deposit-frequency.service';
import { DepositFrequency } from '../../../../../../main/webapp/app/entities/deposit-frequency/deposit-frequency.model';

describe('Component Tests', () => {

    describe('DepositFrequency Management Detail Component', () => {
        let comp: DepositFrequencyDetailComponent;
        let fixture: ComponentFixture<DepositFrequencyDetailComponent>;
        let service: DepositFrequencyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [DepositFrequencyDetailComponent],
                providers: [
                    DepositFrequencyService
                ]
            })
            .overrideTemplate(DepositFrequencyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DepositFrequencyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepositFrequencyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new DepositFrequency(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.depositFrequency).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
