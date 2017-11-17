#!/bin/sh

set -x

oc cluster up
oc login -u system:admin
oc policy add-role-to-user system:image-pusher developer
oc create -f alltemplates.yaml -n openshift
oc login -u developer
oc new-project hogarama
oc create is hogajama
oc create is fluentd
OPENSHIFT_TOKEN=$(oc whoami -t)
oc process -f hogaramaOhneHost.yaml OPENSHIFT_AUTH_TOKEN=$OPENSHIFT_TOKEN | oc create -f -
