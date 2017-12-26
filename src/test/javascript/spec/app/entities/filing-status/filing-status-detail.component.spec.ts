/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { FilingStatusDetailComponent } from '../../../../../../main/webapp/app/entities/filing-status/filing-status-detail.component';
import { FilingStatusService } from '../../../../../../main/webapp/app/entities/filing-status/filing-status.service';
import { FilingStatus } from '../../../../../../main/webapp/app/entities/filing-status/filing-status.model';

describe('Component Tests', () => {

    describe('FilingStatus Management Detail Component', () => {
        let comp: FilingStatusDetailComponent;
        let fixture: ComponentFixture<FilingStatusDetailComponent>;
        let service: FilingStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [FilingStatusDetailComponent],
                providers: [
                    FilingStatusService
                ]
            })
            .overrideTemplate(FilingStatusDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FilingStatusDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilingStatusService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new FilingStatus(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.filingStatus).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
