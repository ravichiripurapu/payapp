/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { AnnualReportsDetailComponent } from '../../../../../../main/webapp/app/entities/annual-reports/annual-reports-detail.component';
import { AnnualReportsService } from '../../../../../../main/webapp/app/entities/annual-reports/annual-reports.service';
import { AnnualReports } from '../../../../../../main/webapp/app/entities/annual-reports/annual-reports.model';

describe('Component Tests', () => {

    describe('AnnualReports Management Detail Component', () => {
        let comp: AnnualReportsDetailComponent;
        let fixture: ComponentFixture<AnnualReportsDetailComponent>;
        let service: AnnualReportsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [AnnualReportsDetailComponent],
                providers: [
                    AnnualReportsService
                ]
            })
            .overrideTemplate(AnnualReportsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnnualReportsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnnualReportsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new AnnualReports(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.annualReports).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
