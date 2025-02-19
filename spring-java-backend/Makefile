.PHONY: install run

install:
	docker-compose -f docker-compose-install.yml run --rm install

run:
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml down &&\
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml rm &&\
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml build --no-cache &&\
	docker-compose -f docker-compose.yml -f docker-compose-dev.yml up -d &&\
	docker logs -f classplanner_app
